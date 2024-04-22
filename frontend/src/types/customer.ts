import {type Tag, type TagCreate} from '@/types/tag';

export interface CustomerCreate {
  customerName: string;
  tags: TagCreate[];
}


export interface Customer extends CustomerCreate  {
  id: number;
  customerName: string;
  validFlag: boolean;
  insertDateTime: string;
  updateDateTime: string;
  tags: Tag[];
}
