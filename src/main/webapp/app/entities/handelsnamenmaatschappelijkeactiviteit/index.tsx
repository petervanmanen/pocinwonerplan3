import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Handelsnamenmaatschappelijkeactiviteit from './handelsnamenmaatschappelijkeactiviteit';
import HandelsnamenmaatschappelijkeactiviteitDetail from './handelsnamenmaatschappelijkeactiviteit-detail';
import HandelsnamenmaatschappelijkeactiviteitUpdate from './handelsnamenmaatschappelijkeactiviteit-update';
import HandelsnamenmaatschappelijkeactiviteitDeleteDialog from './handelsnamenmaatschappelijkeactiviteit-delete-dialog';

const HandelsnamenmaatschappelijkeactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Handelsnamenmaatschappelijkeactiviteit />} />
    <Route path="new" element={<HandelsnamenmaatschappelijkeactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<HandelsnamenmaatschappelijkeactiviteitDetail />} />
      <Route path="edit" element={<HandelsnamenmaatschappelijkeactiviteitUpdate />} />
      <Route path="delete" element={<HandelsnamenmaatschappelijkeactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HandelsnamenmaatschappelijkeactiviteitRoutes;
