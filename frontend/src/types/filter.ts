export interface CustomerSearchParams {
  customerName?: string; 
  tagIds?: number[]; 
  insertDateFrom?: string; 
  insertDateTo?: string; 
  updateDateFrom?: string; 
  updateDateTo?: string; 
  pageNum?: number; 
  pageSize?: number; 
}