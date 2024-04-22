import { type Tag } from "@/types/tag";

export interface Customer {
  id: number;
  customerName: string;
  insertDateTime: string;
  updateDateTime: string;
  tags: Tag[];
}
