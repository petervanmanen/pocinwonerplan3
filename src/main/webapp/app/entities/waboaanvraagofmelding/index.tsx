import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Waboaanvraagofmelding from './waboaanvraagofmelding';
import WaboaanvraagofmeldingDetail from './waboaanvraagofmelding-detail';
import WaboaanvraagofmeldingUpdate from './waboaanvraagofmelding-update';
import WaboaanvraagofmeldingDeleteDialog from './waboaanvraagofmelding-delete-dialog';

const WaboaanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Waboaanvraagofmelding />} />
    <Route path="new" element={<WaboaanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<WaboaanvraagofmeldingDetail />} />
      <Route path="edit" element={<WaboaanvraagofmeldingUpdate />} />
      <Route path="delete" element={<WaboaanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WaboaanvraagofmeldingRoutes;
