import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beslissing from './beslissing';
import BeslissingDetail from './beslissing-detail';
import BeslissingUpdate from './beslissing-update';
import BeslissingDeleteDialog from './beslissing-delete-dialog';

const BeslissingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beslissing />} />
    <Route path="new" element={<BeslissingUpdate />} />
    <Route path=":id">
      <Route index element={<BeslissingDetail />} />
      <Route path="edit" element={<BeslissingUpdate />} />
      <Route path="delete" element={<BeslissingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeslissingRoutes;
