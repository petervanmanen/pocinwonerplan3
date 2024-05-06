import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Dienstverband from './dienstverband';
import DienstverbandDetail from './dienstverband-detail';
import DienstverbandUpdate from './dienstverband-update';
import DienstverbandDeleteDialog from './dienstverband-delete-dialog';

const DienstverbandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Dienstverband />} />
    <Route path="new" element={<DienstverbandUpdate />} />
    <Route path=":id">
      <Route index element={<DienstverbandDetail />} />
      <Route path="edit" element={<DienstverbandUpdate />} />
      <Route path="delete" element={<DienstverbandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DienstverbandRoutes;
