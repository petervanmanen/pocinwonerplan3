import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locatieaanduidingwozobject from './locatieaanduidingwozobject';
import LocatieaanduidingwozobjectDetail from './locatieaanduidingwozobject-detail';
import LocatieaanduidingwozobjectUpdate from './locatieaanduidingwozobject-update';
import LocatieaanduidingwozobjectDeleteDialog from './locatieaanduidingwozobject-delete-dialog';

const LocatieaanduidingwozobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locatieaanduidingwozobject />} />
    <Route path="new" element={<LocatieaanduidingwozobjectUpdate />} />
    <Route path=":id">
      <Route index element={<LocatieaanduidingwozobjectDetail />} />
      <Route path="edit" element={<LocatieaanduidingwozobjectUpdate />} />
      <Route path="delete" element={<LocatieaanduidingwozobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocatieaanduidingwozobjectRoutes;
