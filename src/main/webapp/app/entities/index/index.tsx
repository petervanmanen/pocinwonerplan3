import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Index from './index';
import IndexDetail from './index-detail';
import IndexUpdate from './index-update';
import IndexDeleteDialog from './index-delete-dialog';

const IndexRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Index />} />
    <Route path="new" element={<IndexUpdate />} />
    <Route path=":id">
      <Route index element={<IndexDetail />} />
      <Route path="edit" element={<IndexUpdate />} />
      <Route path="delete" element={<IndexDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndexRoutes;
