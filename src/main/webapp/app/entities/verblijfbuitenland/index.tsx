import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfbuitenland from './verblijfbuitenland';
import VerblijfbuitenlandDetail from './verblijfbuitenland-detail';
import VerblijfbuitenlandUpdate from './verblijfbuitenland-update';
import VerblijfbuitenlandDeleteDialog from './verblijfbuitenland-delete-dialog';

const VerblijfbuitenlandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfbuitenland />} />
    <Route path="new" element={<VerblijfbuitenlandUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfbuitenlandDetail />} />
      <Route path="edit" element={<VerblijfbuitenlandUpdate />} />
      <Route path="delete" element={<VerblijfbuitenlandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfbuitenlandRoutes;
