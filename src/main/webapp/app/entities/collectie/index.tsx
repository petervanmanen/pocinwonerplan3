import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Collectie from './collectie';
import CollectieDetail from './collectie-detail';
import CollectieUpdate from './collectie-update';
import CollectieDeleteDialog from './collectie-delete-dialog';

const CollectieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Collectie />} />
    <Route path="new" element={<CollectieUpdate />} />
    <Route path=":id">
      <Route index element={<CollectieDetail />} />
      <Route path="edit" element={<CollectieUpdate />} />
      <Route path="delete" element={<CollectieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CollectieRoutes;
