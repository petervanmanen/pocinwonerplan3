import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Archiefcategorie from './archiefcategorie';
import ArchiefcategorieDetail from './archiefcategorie-detail';
import ArchiefcategorieUpdate from './archiefcategorie-update';
import ArchiefcategorieDeleteDialog from './archiefcategorie-delete-dialog';

const ArchiefcategorieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Archiefcategorie />} />
    <Route path="new" element={<ArchiefcategorieUpdate />} />
    <Route path=":id">
      <Route index element={<ArchiefcategorieDetail />} />
      <Route path="edit" element={<ArchiefcategorieUpdate />} />
      <Route path="delete" element={<ArchiefcategorieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArchiefcategorieRoutes;
