import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nederlandsenationaliteitingeschrevenpersoon from './nederlandsenationaliteitingeschrevenpersoon';
import NederlandsenationaliteitingeschrevenpersoonDetail from './nederlandsenationaliteitingeschrevenpersoon-detail';
import NederlandsenationaliteitingeschrevenpersoonUpdate from './nederlandsenationaliteitingeschrevenpersoon-update';
import NederlandsenationaliteitingeschrevenpersoonDeleteDialog from './nederlandsenationaliteitingeschrevenpersoon-delete-dialog';

const NederlandsenationaliteitingeschrevenpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nederlandsenationaliteitingeschrevenpersoon />} />
    <Route path="new" element={<NederlandsenationaliteitingeschrevenpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NederlandsenationaliteitingeschrevenpersoonDetail />} />
      <Route path="edit" element={<NederlandsenationaliteitingeschrevenpersoonUpdate />} />
      <Route path="delete" element={<NederlandsenationaliteitingeschrevenpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NederlandsenationaliteitingeschrevenpersoonRoutes;
