import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ouderofverzorger from './ouderofverzorger';
import OuderofverzorgerDetail from './ouderofverzorger-detail';
import OuderofverzorgerUpdate from './ouderofverzorger-update';
import OuderofverzorgerDeleteDialog from './ouderofverzorger-delete-dialog';

const OuderofverzorgerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ouderofverzorger />} />
    <Route path="new" element={<OuderofverzorgerUpdate />} />
    <Route path=":id">
      <Route index element={<OuderofverzorgerDetail />} />
      <Route path="edit" element={<OuderofverzorgerUpdate />} />
      <Route path="delete" element={<OuderofverzorgerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OuderofverzorgerRoutes;
