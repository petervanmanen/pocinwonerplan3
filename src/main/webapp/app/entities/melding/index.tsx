import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Melding from './melding';
import MeldingDetail from './melding-detail';
import MeldingUpdate from './melding-update';
import MeldingDeleteDialog from './melding-delete-dialog';

const MeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Melding />} />
    <Route path="new" element={<MeldingUpdate />} />
    <Route path=":id">
      <Route index element={<MeldingDetail />} />
      <Route path="edit" element={<MeldingUpdate />} />
      <Route path="delete" element={<MeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MeldingRoutes;
