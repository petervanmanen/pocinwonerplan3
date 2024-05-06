import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Archiefstuk from './archiefstuk';
import ArchiefstukDetail from './archiefstuk-detail';
import ArchiefstukUpdate from './archiefstuk-update';
import ArchiefstukDeleteDialog from './archiefstuk-delete-dialog';

const ArchiefstukRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Archiefstuk />} />
    <Route path="new" element={<ArchiefstukUpdate />} />
    <Route path=":id">
      <Route index element={<ArchiefstukDetail />} />
      <Route path="edit" element={<ArchiefstukUpdate />} />
      <Route path="delete" element={<ArchiefstukDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArchiefstukRoutes;
