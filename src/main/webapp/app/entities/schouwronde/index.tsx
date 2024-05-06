import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Schouwronde from './schouwronde';
import SchouwrondeDetail from './schouwronde-detail';
import SchouwrondeUpdate from './schouwronde-update';
import SchouwrondeDeleteDialog from './schouwronde-delete-dialog';

const SchouwrondeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Schouwronde />} />
    <Route path="new" element={<SchouwrondeUpdate />} />
    <Route path=":id">
      <Route index element={<SchouwrondeDetail />} />
      <Route path="edit" element={<SchouwrondeUpdate />} />
      <Route path="delete" element={<SchouwrondeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SchouwrondeRoutes;
