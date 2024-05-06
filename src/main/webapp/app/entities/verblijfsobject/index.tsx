import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfsobject from './verblijfsobject';
import VerblijfsobjectDetail from './verblijfsobject-detail';
import VerblijfsobjectUpdate from './verblijfsobject-update';
import VerblijfsobjectDeleteDialog from './verblijfsobject-delete-dialog';

const VerblijfsobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfsobject />} />
    <Route path="new" element={<VerblijfsobjectUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfsobjectDetail />} />
      <Route path="edit" element={<VerblijfsobjectUpdate />} />
      <Route path="delete" element={<VerblijfsobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfsobjectRoutes;
