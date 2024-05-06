import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Locatieaanduidingadreswozobject from './locatieaanduidingadreswozobject';
import LocatieaanduidingadreswozobjectDetail from './locatieaanduidingadreswozobject-detail';
import LocatieaanduidingadreswozobjectUpdate from './locatieaanduidingadreswozobject-update';
import LocatieaanduidingadreswozobjectDeleteDialog from './locatieaanduidingadreswozobject-delete-dialog';

const LocatieaanduidingadreswozobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Locatieaanduidingadreswozobject />} />
    <Route path="new" element={<LocatieaanduidingadreswozobjectUpdate />} />
    <Route path=":id">
      <Route index element={<LocatieaanduidingadreswozobjectDetail />} />
      <Route path="edit" element={<LocatieaanduidingadreswozobjectUpdate />} />
      <Route path="delete" element={<LocatieaanduidingadreswozobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LocatieaanduidingadreswozobjectRoutes;
