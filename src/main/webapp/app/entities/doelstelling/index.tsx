import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doelstelling from './doelstelling';
import DoelstellingDetail from './doelstelling-detail';
import DoelstellingUpdate from './doelstelling-update';
import DoelstellingDeleteDialog from './doelstelling-delete-dialog';

const DoelstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doelstelling />} />
    <Route path="new" element={<DoelstellingUpdate />} />
    <Route path=":id">
      <Route index element={<DoelstellingDetail />} />
      <Route path="edit" element={<DoelstellingUpdate />} />
      <Route path="delete" element={<DoelstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoelstellingRoutes;
