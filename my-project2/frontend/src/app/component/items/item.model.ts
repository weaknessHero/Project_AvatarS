import {Column, Entity, PrimaryGeneratedColumn} from "typeorm";

@Entity()
export class Item {
  @PrimaryGeneratedColumn()
  id: number;
  @Column()
  name: string;
  @Column()
  category: string;
  @Column()
  brand: string;
  @Column()
  price: number;
  @Column()
  imageUrl: string;
  @Column()
  buyUrl: string;
  @Column()
  tags: string[];

  constructor(id: number, name: string, brand:string, category: string, price: number, imageUrl: string, buyUrl: string, tags: string[]) {
    this.id = id;
    this.name = name;
    this.brand = brand;
    this.category = category;
    this.price = price;
    this.imageUrl = imageUrl;
    this.buyUrl = buyUrl;
    if (tags && tags.length > 0) {
      this.tags = tags; // 실제 태그 값
    } else {
      // 기본 태그 값
      this.tags = ['기본 태그1', '기본 태그2'];
    }
  }
}
