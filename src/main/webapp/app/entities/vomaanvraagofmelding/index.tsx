import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vomaanvraagofmelding from './vomaanvraagofmelding';
import VomaanvraagofmeldingDetail from './vomaanvraagofmelding-detail';
import VomaanvraagofmeldingUpdate from './vomaanvraagofmelding-update';
import VomaanvraagofmeldingDeleteDialog from './vomaanvraagofmelding-delete-dialog';

const VomaanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vomaanvraagofmelding />} />
    <Route path="new" element={<VomaanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<VomaanvraagofmeldingDetail />} />
      <Route path="edit" element={<VomaanvraagofmeldingUpdate />} />
      <Route path="delete" element={<VomaanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VomaanvraagofmeldingRoutes;
