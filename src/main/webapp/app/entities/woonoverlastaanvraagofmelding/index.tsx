import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Woonoverlastaanvraagofmelding from './woonoverlastaanvraagofmelding';
import WoonoverlastaanvraagofmeldingDetail from './woonoverlastaanvraagofmelding-detail';
import WoonoverlastaanvraagofmeldingUpdate from './woonoverlastaanvraagofmelding-update';
import WoonoverlastaanvraagofmeldingDeleteDialog from './woonoverlastaanvraagofmelding-delete-dialog';

const WoonoverlastaanvraagofmeldingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Woonoverlastaanvraagofmelding />} />
    <Route path="new" element={<WoonoverlastaanvraagofmeldingUpdate />} />
    <Route path=":id">
      <Route index element={<WoonoverlastaanvraagofmeldingDetail />} />
      <Route path="edit" element={<WoonoverlastaanvraagofmeldingUpdate />} />
      <Route path="delete" element={<WoonoverlastaanvraagofmeldingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WoonoverlastaanvraagofmeldingRoutes;
