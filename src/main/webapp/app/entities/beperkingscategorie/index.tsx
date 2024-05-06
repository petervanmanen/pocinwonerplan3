import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beperkingscategorie from './beperkingscategorie';
import BeperkingscategorieDetail from './beperkingscategorie-detail';
import BeperkingscategorieUpdate from './beperkingscategorie-update';
import BeperkingscategorieDeleteDialog from './beperkingscategorie-delete-dialog';

const BeperkingscategorieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beperkingscategorie />} />
    <Route path="new" element={<BeperkingscategorieUpdate />} />
    <Route path=":id">
      <Route index element={<BeperkingscategorieDetail />} />
      <Route path="edit" element={<BeperkingscategorieUpdate />} />
      <Route path="delete" element={<BeperkingscategorieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeperkingscategorieRoutes;
