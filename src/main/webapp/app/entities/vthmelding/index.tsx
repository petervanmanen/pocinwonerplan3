import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vthmelding from './vthmelding';
import VthmeldingDetail from './vthmelding-detail';
import VthmeldingUpdate from './vthmelding-update';
import VthmeldingDeleteDialog from './vthmelding-delete-dialog';

const VthmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vthmelding />} />
    <Route path="new" element={<VthmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<VthmeldingDetail />} />
      <Route path="edit" element={<VthmeldingUpdate />} />
      <Route path="delete" element={<VthmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VthmeldingRoutes;
