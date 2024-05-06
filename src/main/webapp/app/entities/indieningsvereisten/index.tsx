import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Indieningsvereisten from './indieningsvereisten';
import IndieningsvereistenDetail from './indieningsvereisten-detail';
import IndieningsvereistenUpdate from './indieningsvereisten-update';
import IndieningsvereistenDeleteDialog from './indieningsvereisten-delete-dialog';

const IndieningsvereistenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Indieningsvereisten />} />
    <Route path="new" element={<IndieningsvereistenUpdate />} />
    <Route path=":id">
      <Route index element={<IndieningsvereistenDetail />} />
      <Route path="edit" element={<IndieningsvereistenUpdate />} />
      <Route path="delete" element={<IndieningsvereistenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndieningsvereistenRoutes;
