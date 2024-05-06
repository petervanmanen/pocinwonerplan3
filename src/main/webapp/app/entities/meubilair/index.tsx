import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Meubilair from './meubilair';
import MeubilairDetail from './meubilair-detail';
import MeubilairUpdate from './meubilair-update';
import MeubilairDeleteDialog from './meubilair-delete-dialog';

const MeubilairRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Meubilair />} />
    <Route path="new" element={<MeubilairUpdate />} />
    <Route path=":id">
      <Route index element={<MeubilairDetail />} />
      <Route path="edit" element={<MeubilairUpdate />} />
      <Route path="delete" element={<MeubilairDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MeubilairRoutes;
