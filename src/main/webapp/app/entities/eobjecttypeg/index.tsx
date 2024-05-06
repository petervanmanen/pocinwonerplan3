import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypeg from './eobjecttypeg';
import EobjecttypegDetail from './eobjecttypeg-detail';
import EobjecttypegUpdate from './eobjecttypeg-update';
import EobjecttypegDeleteDialog from './eobjecttypeg-delete-dialog';

const EobjecttypegRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypeg />} />
    <Route path="new" element={<EobjecttypegUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypegDetail />} />
      <Route path="edit" element={<EobjecttypegUpdate />} />
      <Route path="delete" element={<EobjecttypegDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypegRoutes;
