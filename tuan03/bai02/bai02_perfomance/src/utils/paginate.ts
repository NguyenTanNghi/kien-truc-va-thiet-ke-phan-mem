/**
 * Interface cho kết quả phân trang
 */
export interface PaginationResult {
    page: number;
    limit: number;
    totalItems: number;
    totalPages: number;
    hasNext: boolean;
    hasPrev: boolean;
}

/**
 * Tính toán thông tin phân trang
 */
export function calculatePagination(
    page: number,
    limit: number,
    totalItems: number
): PaginationResult {
    const totalPages = Math.ceil(totalItems / limit);

    return {
        page,
        limit,
        totalItems,
        totalPages,
        hasNext: page < totalPages,
        hasPrev: page > 1,
    };
}

/**
 * Lấy offset để slice array
 */
export function getOffset(page: number, limit: number): number {
    return (page - 1) * limit;
}
