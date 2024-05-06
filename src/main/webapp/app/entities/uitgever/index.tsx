import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitgever from './uitgever';
import UitgeverDetail from './uitgever-detail';
import UitgeverUpdate from './uitgever-update';
import UitgeverDeleteDialog from './uitgever-delete-dialog';

const UitgeverRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitgever />} />
    <Route path="new" element={<UitgeverUpdate />} />
    <Route path=":id">
      <Route index element={<UitgeverDetail />} />
      <Route path="edit" element={<UitgeverUpdate />} />
      <Route path="delete" element={<UitgeverDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitgeverRoutes;
