import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omgevingswaarde from './omgevingswaarde';
import OmgevingswaardeDetail from './omgevingswaarde-detail';
import OmgevingswaardeUpdate from './omgevingswaarde-update';
import OmgevingswaardeDeleteDialog from './omgevingswaarde-delete-dialog';

const OmgevingswaardeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omgevingswaarde />} />
    <Route path="new" element={<OmgevingswaardeUpdate />} />
    <Route path=":id">
      <Route index element={<OmgevingswaardeDetail />} />
      <Route path="edit" element={<OmgevingswaardeUpdate />} />
      <Route path="delete" element={<OmgevingswaardeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmgevingswaardeRoutes;
