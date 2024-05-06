import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfbuitenlandsubject from './verblijfbuitenlandsubject';
import VerblijfbuitenlandsubjectDetail from './verblijfbuitenlandsubject-detail';
import VerblijfbuitenlandsubjectUpdate from './verblijfbuitenlandsubject-update';
import VerblijfbuitenlandsubjectDeleteDialog from './verblijfbuitenlandsubject-delete-dialog';

const VerblijfbuitenlandsubjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfbuitenlandsubject />} />
    <Route path="new" element={<VerblijfbuitenlandsubjectUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfbuitenlandsubjectDetail />} />
      <Route path="edit" element={<VerblijfbuitenlandsubjectUpdate />} />
      <Route path="delete" element={<VerblijfbuitenlandsubjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfbuitenlandsubjectRoutes;
