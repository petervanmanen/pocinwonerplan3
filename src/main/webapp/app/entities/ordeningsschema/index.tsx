import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ordeningsschema from './ordeningsschema';
import OrdeningsschemaDetail from './ordeningsschema-detail';
import OrdeningsschemaUpdate from './ordeningsschema-update';
import OrdeningsschemaDeleteDialog from './ordeningsschema-delete-dialog';

const OrdeningsschemaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ordeningsschema />} />
    <Route path="new" element={<OrdeningsschemaUpdate />} />
    <Route path=":id">
      <Route index element={<OrdeningsschemaDetail />} />
      <Route path="edit" element={<OrdeningsschemaUpdate />} />
      <Route path="delete" element={<OrdeningsschemaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrdeningsschemaRoutes;
