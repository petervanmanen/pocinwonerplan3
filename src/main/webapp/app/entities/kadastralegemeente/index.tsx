import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kadastralegemeente from './kadastralegemeente';
import KadastralegemeenteDetail from './kadastralegemeente-detail';
import KadastralegemeenteUpdate from './kadastralegemeente-update';
import KadastralegemeenteDeleteDialog from './kadastralegemeente-delete-dialog';

const KadastralegemeenteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kadastralegemeente />} />
    <Route path="new" element={<KadastralegemeenteUpdate />} />
    <Route path=":id">
      <Route index element={<KadastralegemeenteDetail />} />
      <Route path="edit" element={<KadastralegemeenteUpdate />} />
      <Route path="delete" element={<KadastralegemeenteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KadastralegemeenteRoutes;
