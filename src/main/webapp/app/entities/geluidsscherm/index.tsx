import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Geluidsscherm from './geluidsscherm';
import GeluidsschermDetail from './geluidsscherm-detail';
import GeluidsschermUpdate from './geluidsscherm-update';
import GeluidsschermDeleteDialog from './geluidsscherm-delete-dialog';

const GeluidsschermRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Geluidsscherm />} />
    <Route path="new" element={<GeluidsschermUpdate />} />
    <Route path=":id">
      <Route index element={<GeluidsschermDetail />} />
      <Route path="edit" element={<GeluidsschermUpdate />} />
      <Route path="delete" element={<GeluidsschermDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeluidsschermRoutes;
