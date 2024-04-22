export interface Auth {
  token: string;
  userinfo: UserInfo;
}

export interface UserInfo {
  id: number;
  name: string;
  mail: string;
}
