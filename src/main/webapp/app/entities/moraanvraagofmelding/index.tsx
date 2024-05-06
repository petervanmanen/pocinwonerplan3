import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Moraanvraagofmelding from './moraanvraagofmelding';
import MoraanvraagofmeldingDetail from './moraanvraagofmelding-detail';
import MoraanvraagofmeldingUpdate from './moraanvraagofmelding-update';
import MoraanvraagofmeldingDeleteDialog from './moraanvraagofmelding-delete-dialog';

const MoraanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Moraanvraagofmelding />} />
    <Route path="new" element={<MoraanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<MoraanvraagofmeldingDetail />} />
      <Route path="edit" element={<MoraanvraagofmeldingUpdate />} />
      <Route path="delete" element={<MoraanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MoraanvraagofmeldingRoutes;
