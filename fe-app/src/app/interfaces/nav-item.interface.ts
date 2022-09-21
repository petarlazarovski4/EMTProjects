export interface NavItem {
  name: string;
  icon: string;
  route?: string;
  canAccess?: boolean,
  autoClose?: boolean,
  children?: NavItem[];
}
