import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypea from './eobjecttypea';
import EobjecttypeaDetail from './eobjecttypea-detail';
import EobjecttypeaUpdate from './eobjecttypea-update';
import EobjecttypeaDeleteDialog from './eobjecttypea-delete-dialog';

const EobjecttypeaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypea />} />
    <Route path="new" element={<EobjecttypeaUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypeaDetail />} />
      <Route path="edit" element={<EobjecttypeaUpdate />} />
      <Route path="delete" element={<EobjecttypeaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypeaRoutes;
