import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttyped from './eobjecttyped';
import EobjecttypedDetail from './eobjecttyped-detail';
import EobjecttypedUpdate from './eobjecttyped-update';
import EobjecttypedDeleteDialog from './eobjecttyped-delete-dialog';

const EobjecttypedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttyped />} />
    <Route path="new" element={<EobjecttypedUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypedDetail />} />
      <Route path="edit" element={<EobjecttypedUpdate />} />
      <Route path="delete" element={<EobjecttypedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypedRoutes;
