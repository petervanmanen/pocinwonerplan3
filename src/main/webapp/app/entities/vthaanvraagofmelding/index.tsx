import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vthaanvraagofmelding from './vthaanvraagofmelding';
import VthaanvraagofmeldingDetail from './vthaanvraagofmelding-detail';
import VthaanvraagofmeldingUpdate from './vthaanvraagofmelding-update';
import VthaanvraagofmeldingDeleteDialog from './vthaanvraagofmelding-delete-dialog';

const VthaanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vthaanvraagofmelding />} />
    <Route path="new" element={<VthaanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<VthaanvraagofmeldingDetail />} />
      <Route path="edit" element={<VthaanvraagofmeldingUpdate />} />
      <Route path="delete" element={<VthaanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VthaanvraagofmeldingRoutes;
