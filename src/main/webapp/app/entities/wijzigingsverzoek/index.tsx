import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wijzigingsverzoek from './wijzigingsverzoek';
import WijzigingsverzoekDetail from './wijzigingsverzoek-detail';
import WijzigingsverzoekUpdate from './wijzigingsverzoek-update';
import WijzigingsverzoekDeleteDialog from './wijzigingsverzoek-delete-dialog';

const WijzigingsverzoekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wijzigingsverzoek />} />
    <Route path="new" element={<WijzigingsverzoekUpdate />} />
    <Route path=":id">
      <Route index element={<WijzigingsverzoekDetail />} />
      <Route path="edit" element={<WijzigingsverzoekUpdate />} />
      <Route path="delete" element={<WijzigingsverzoekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WijzigingsverzoekRoutes;
