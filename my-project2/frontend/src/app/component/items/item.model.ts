export class Item {
  id: number;
  name: string;
  brand: string;
  imageUrl: string;
  buyUrl: string;
  tags: string[];

  constructor(id: number, name: string, brand:string, imageUrl: string, buyUrl: string, tags: string[]) {
    this.id = id;
    this.name = name;
    this.brand = brand;
    this.imageUrl = imageUrl;
    this.buyUrl = buyUrl;
    this.tags = tags || [];
  }
}
