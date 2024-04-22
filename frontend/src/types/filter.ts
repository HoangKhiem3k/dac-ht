export interface CustomerSearchParams {
  customerName?: string | null;
  tagIds?: number[] | null;
  insertDateFrom?: string | null;
  insertDateTo?: string | null;
  updateDateFrom?: string | null;
  updateDateTo?: string | null;
  pageNum?: number | null;
  pageSize?: number | null;
  sort?: {
    property: string;
    asc: boolean;
  } | null;
}
export interface TagSearchParams {
  tagName?: string | null;
}
export interface SortParams {
  sort?: {
    property: string;
    asc: boolean;
  } | null;
}
