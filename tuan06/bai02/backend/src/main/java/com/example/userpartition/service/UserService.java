package com.example.userpartition.service;

import com.example.userpartition.dto.UserRequestDTO;
import com.example.userpartition.dto.UserResponseDTO;
import com.example.userpartition.entity.*;
import com.example.userpartition.exception.ResourceNotFoundException;
import com.example.userpartition.exception.DuplicateEmailException;
import com.example.userpartition.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMaleRepository userMaleRepository;
    private final UserFemaleRepository userFemaleRepository;
    private final UserCoreRepository userCoreRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        // Check if email already exists in both horizontal partitions
        if (userMaleRepository.existsByEmail(requestDTO.getEmail()) ||
                userFemaleRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + requestDTO.getEmail());
        }

        // Horizontal Partitioning: Route based on gender
        Long horizontalId;
        LocalDateTime createdAt = LocalDateTime.now();

        if ("male".equalsIgnoreCase(requestDTO.getGender())) {
            UserMale userMale = new UserMale();
            userMale.setName(requestDTO.getName());
            userMale.setEmail(requestDTO.getEmail());
            userMale.setGender("male");
            userMale.setCreatedAt(createdAt);
            userMale = userMaleRepository.save(userMale);
            horizontalId = userMale.getId();
        } else {
            UserFemale userFemale = new UserFemale();
            userFemale.setName(requestDTO.getName());
            userFemale.setEmail(requestDTO.getEmail());
            userFemale.setGender("female");
            userFemale.setCreatedAt(createdAt);
            userFemale = userFemaleRepository.save(userFemale);
            horizontalId = userFemale.getId();
        }

        // Vertical Partitioning: Save to user_core and user_profile
        UserCore userCore = new UserCore();
        userCore.setId(horizontalId);
        userCore.setName(requestDTO.getName());
        userCore.setEmail(requestDTO.getEmail());
        userCore.setGender(requestDTO.getGender().toLowerCase());
        userCoreRepository.save(userCore);

        UserProfile userProfile = new UserProfile();
        userProfile.setId(horizontalId);
        userProfile.setAvatar(requestDTO.getAvatar());
        userProfile.setBio(requestDTO.getBio());
        userProfileRepository.save(userProfile);

        return UserResponseDTO.builder()
                .id(horizontalId)
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .gender(requestDTO.getGender().toLowerCase())
                .avatar(requestDTO.getAvatar())
                .bio(requestDTO.getBio())
                .createdAt(createdAt)
                .build();
    }

    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> allUsers = new ArrayList<>();

        // Merge data from both horizontal partitions
        List<UserMale> maleUsers = userMaleRepository.findAll();
        List<UserFemale> femaleUsers = userFemaleRepository.findAll();

        // Convert male users
        for (UserMale male : maleUsers) {
            UserProfile profile = userProfileRepository.findById(male.getId()).orElse(null);
            allUsers.add(UserResponseDTO.builder()
                    .id(male.getId())
                    .name(male.getName())
                    .email(male.getEmail())
                    .gender(male.getGender())
                    .avatar(profile != null ? profile.getAvatar() : null)
                    .bio(profile != null ? profile.getBio() : null)
                    .createdAt(male.getCreatedAt())
                    .build());
        }

        // Convert female users
        for (UserFemale female : femaleUsers) {
            UserProfile profile = userProfileRepository.findById(female.getId()).orElse(null);
            allUsers.add(UserResponseDTO.builder()
                    .id(female.getId())
                    .name(female.getName())
                    .email(female.getEmail())
                    .gender(female.getGender())
                    .avatar(profile != null ? profile.getAvatar() : null)
                    .bio(profile != null ? profile.getBio() : null)
                    .createdAt(female.getCreatedAt())
                    .build());
        }

        return allUsers;
    }

    public UserResponseDTO getUserById(Long id) {
        // Try to find in male partition first
        UserMale userMale = userMaleRepository.findById(id).orElse(null);
        if (userMale != null) {
            UserProfile profile = userProfileRepository.findById(id).orElse(null);
            return UserResponseDTO.builder()
                    .id(userMale.getId())
                    .name(userMale.getName())
                    .email(userMale.getEmail())
                    .gender(userMale.getGender())
                    .avatar(profile != null ? profile.getAvatar() : null)
                    .bio(profile != null ? profile.getBio() : null)
                    .createdAt(userMale.getCreatedAt())
                    .build();
        }

        // Try female partition
        UserFemale userFemale = userFemaleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        UserProfile profile = userProfileRepository.findById(id).orElse(null);
        return UserResponseDTO.builder()
                .id(userFemale.getId())
                .name(userFemale.getName())
                .email(userFemale.getEmail())
                .gender(userFemale.getGender())
                .avatar(profile != null ? profile.getAvatar() : null)
                .bio(profile != null ? profile.getBio() : null)
                .createdAt(userFemale.getCreatedAt())
                .build();
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        // Find user in horizontal partitions
        UserMale userMale = userMaleRepository.findById(id).orElse(null);
        UserFemale userFemale = userFemaleRepository.findById(id).orElse(null);

        if (userMale == null && userFemale == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        LocalDateTime createdAt;

        // Handle gender change (requires moving between partitions)
        if (userMale != null && "female".equalsIgnoreCase(requestDTO.getGender())) {
            // Move from male to female partition
            createdAt = userMale.getCreatedAt();
            userMaleRepository.delete(userMale);

            UserFemale newFemale = new UserFemale();
            newFemale.setId(id);
            newFemale.setName(requestDTO.getName());
            newFemale.setEmail(requestDTO.getEmail());
            newFemale.setGender("female");
            newFemale.setCreatedAt(createdAt);
            userFemaleRepository.save(newFemale);
        } else if (userFemale != null && "male".equalsIgnoreCase(requestDTO.getGender())) {
            // Move from female to male partition
            createdAt = userFemale.getCreatedAt();
            userFemaleRepository.delete(userFemale);

            UserMale newMale = new UserMale();
            newMale.setId(id);
            newMale.setName(requestDTO.getName());
            newMale.setEmail(requestDTO.getEmail());
            newMale.setGender("male");
            newMale.setCreatedAt(createdAt);
            userMaleRepository.save(newMale);
        } else {
            // Update in the same partition
            if (userMale != null) {
                userMale.setName(requestDTO.getName());
                userMale.setEmail(requestDTO.getEmail());
                userMaleRepository.save(userMale);
                createdAt = userMale.getCreatedAt();
            } else {
                userFemale.setName(requestDTO.getName());
                userFemale.setEmail(requestDTO.getEmail());
                userFemaleRepository.save(userFemale);
                createdAt = userFemale.getCreatedAt();
            }
        }

        // Update vertical partitions
        UserCore userCore = userCoreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCore not found with id: " + id));
        userCore.setName(requestDTO.getName());
        userCore.setEmail(requestDTO.getEmail());
        userCore.setGender(requestDTO.getGender().toLowerCase());
        userCoreRepository.save(userCore);

        UserProfile userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserProfile not found with id: " + id));
        userProfile.setAvatar(requestDTO.getAvatar());
        userProfile.setBio(requestDTO.getBio());
        userProfileRepository.save(userProfile);

        return UserResponseDTO.builder()
                .id(id)
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .gender(requestDTO.getGender().toLowerCase())
                .avatar(requestDTO.getAvatar())
                .bio(requestDTO.getBio())
                .createdAt(createdAt)
                .build();
    }

    @Transactional
    public void deleteUser(Long id) {
        // Delete from horizontal partitions
        boolean deletedFromMale = userMaleRepository.findById(id)
                .map(user -> {
                    userMaleRepository.delete(user);
                    return true;
                }).orElse(false);

        boolean deletedFromFemale = userFemaleRepository.findById(id)
                .map(user -> {
                    userFemaleRepository.delete(user);
                    return true;
                }).orElse(false);

        if (!deletedFromMale && !deletedFromFemale) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        // Delete from vertical partitions
        userCoreRepository.deleteById(id);
        userProfileRepository.deleteById(id);
    }
}
