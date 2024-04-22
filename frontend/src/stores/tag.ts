import { defineStore } from "pinia";
import { type Tag } from "@/types/tag";
import { useCommonStore } from "@/stores/common";
import { api } from "@/services/http";
import { API_BASE_URL } from "@/config";
import { TAG_API_URL } from "@/utils/constants";
import { API_SUCCESS_STATUS_CODE } from "../utils/constants";

export const useTagStore = defineStore({
  id: "tagStore",
  state: () => ({
    tags: [] as Tag[],
  }),
  actions: {
    // Get tags
    async getTags(tagName: string) {
      const { setProgressLoading } = useCommonStore();
      setProgressLoading(true);
      try {
        const res = await api.post<Tag[]>(
          `${API_BASE_URL}${TAG_API_URL}`,
          tagName
        );
        if (res.code === API_SUCCESS_STATUS_CODE) {
          this.tags = res.data;
        } else {
          this.tags = [];
        }
        setProgressLoading(false);
      } catch (e) {
        console.log(e);
        this.emptyTags();
        this.tags = [];
        setProgressLoading(false);
      }
    },
  },
});
