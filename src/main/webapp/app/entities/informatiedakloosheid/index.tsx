import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Informatiedakloosheid from './informatiedakloosheid';
import InformatiedakloosheidDetail from './informatiedakloosheid-detail';
import InformatiedakloosheidUpdate from './informatiedakloosheid-update';
import InformatiedakloosheidDeleteDialog from './informatiedakloosheid-delete-dialog';

const InformatiedakloosheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Informatiedakloosheid />} />
    <Route path="new" element={<InformatiedakloosheidUpdate />} />
    <Route path=":id">
      <Route index element={<InformatiedakloosheidDetail />} />
      <Route path="edit" element={<InformatiedakloosheidUpdate />} />
      <Route path="delete" element={<InformatiedakloosheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InformatiedakloosheidRoutes;
