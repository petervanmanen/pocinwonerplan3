import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Postadres from './postadres';
import PostadresDetail from './postadres-detail';
import PostadresUpdate from './postadres-update';
import PostadresDeleteDialog from './postadres-delete-dialog';

const PostadresRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Postadres />} />
    <Route path="new" element={<PostadresUpdate />} />
    <Route path=":id">
      <Route index element={<PostadresDetail />} />
      <Route path="edit" element={<PostadresUpdate />} />
      <Route path="delete" element={<PostadresDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PostadresRoutes;
