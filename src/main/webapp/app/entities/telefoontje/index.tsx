import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Telefoontje from './telefoontje';
import TelefoontjeDetail from './telefoontje-detail';
import TelefoontjeUpdate from './telefoontje-update';
import TelefoontjeDeleteDialog from './telefoontje-delete-dialog';

const TelefoontjeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Telefoontje />} />
    <Route path="new" element={<TelefoontjeUpdate />} />
    <Route path=":id">
      <Route index element={<TelefoontjeDetail />} />
      <Route path="edit" element={<TelefoontjeUpdate />} />
      <Route path="delete" element={<TelefoontjeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TelefoontjeRoutes;
