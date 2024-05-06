import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Moormelding from './moormelding';
import MoormeldingDetail from './moormelding-detail';
import MoormeldingUpdate from './moormelding-update';
import MoormeldingDeleteDialog from './moormelding-delete-dialog';

const MoormeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Moormelding />} />
    <Route path="new" element={<MoormeldingUpdate />} />
    <Route path=":id">
      <Route index element={<MoormeldingDetail />} />
      <Route path="edit" element={<MoormeldingUpdate />} />
      <Route path="delete" element={<MoormeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MoormeldingRoutes;
