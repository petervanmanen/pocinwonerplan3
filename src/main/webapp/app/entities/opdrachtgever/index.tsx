import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opdrachtgever from './opdrachtgever';
import OpdrachtgeverDetail from './opdrachtgever-detail';
import OpdrachtgeverUpdate from './opdrachtgever-update';
import OpdrachtgeverDeleteDialog from './opdrachtgever-delete-dialog';

const OpdrachtgeverRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opdrachtgever />} />
    <Route path="new" element={<OpdrachtgeverUpdate />} />
    <Route path=":id">
      <Route index element={<OpdrachtgeverDetail />} />
      <Route path="edit" element={<OpdrachtgeverUpdate />} />
      <Route path="delete" element={<OpdrachtgeverDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpdrachtgeverRoutes;
