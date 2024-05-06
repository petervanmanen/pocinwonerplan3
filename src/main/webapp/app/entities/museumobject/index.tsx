import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Museumobject from './museumobject';
import MuseumobjectDetail from './museumobject-detail';
import MuseumobjectUpdate from './museumobject-update';
import MuseumobjectDeleteDialog from './museumobject-delete-dialog';

const MuseumobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Museumobject />} />
    <Route path="new" element={<MuseumobjectUpdate />} />
    <Route path=":id">
      <Route index element={<MuseumobjectDetail />} />
      <Route path="edit" element={<MuseumobjectUpdate />} />
      <Route path="delete" element={<MuseumobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MuseumobjectRoutes;
