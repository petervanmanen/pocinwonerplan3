import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inventaris from './inventaris';
import InventarisDetail from './inventaris-detail';
import InventarisUpdate from './inventaris-update';
import InventarisDeleteDialog from './inventaris-delete-dialog';

const InventarisRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inventaris />} />
    <Route path="new" element={<InventarisUpdate />} />
    <Route path=":id">
      <Route index element={<InventarisDetail />} />
      <Route path="edit" element={<InventarisUpdate />} />
      <Route path="delete" element={<InventarisDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InventarisRoutes;
