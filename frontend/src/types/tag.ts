export interface TagCreate {
  id: number;
  tagName: string;
}
export interface Tag extends TagCreate {
  validFlag: boolean;
  insertDateTime: string;
  updateDateTime: string;
}
