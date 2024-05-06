import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verzuimmelding from './verzuimmelding';
import VerzuimmeldingDetail from './verzuimmelding-detail';
import VerzuimmeldingUpdate from './verzuimmelding-update';
import VerzuimmeldingDeleteDialog from './verzuimmelding-delete-dialog';

const VerzuimmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verzuimmelding />} />
    <Route path="new" element={<VerzuimmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<VerzuimmeldingDetail />} />
      <Route path="edit" element={<VerzuimmeldingUpdate />} />
      <Route path="delete" element={<VerzuimmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerzuimmeldingRoutes;
