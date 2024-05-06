import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omgevingswaarderegel from './omgevingswaarderegel';
import OmgevingswaarderegelDetail from './omgevingswaarderegel-detail';
import OmgevingswaarderegelUpdate from './omgevingswaarderegel-update';
import OmgevingswaarderegelDeleteDialog from './omgevingswaarderegel-delete-dialog';

const OmgevingswaarderegelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omgevingswaarderegel />} />
    <Route path="new" element={<OmgevingswaarderegelUpdate />} />
    <Route path=":id">
      <Route index element={<OmgevingswaarderegelDetail />} />
      <Route path="edit" element={<OmgevingswaarderegelUpdate />} />
      <Route path="delete" element={<OmgevingswaarderegelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmgevingswaarderegelRoutes;
